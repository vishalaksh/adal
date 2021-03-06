/**
 * 23-MAY-2013
 * 
 * Kiev, Ukraine.
 * 
 */
package com.adal

/**
 * @author Alex Evseenko
 *
 */
object Dataflow {
  // transform data from src to dst
  type Transform = (Container, Container) => Boolean

  def apply(transform: Transform) (implicit app: AdalApplication) = new Dataflow(transform)
}

class Dataflow(transform: Dataflow.Transform) (implicit app: AdalApplication) extends AdalTopLevelArtifact {
  app.add(this)


  var src: Option[Container] = None
  var dst: Option[Container] = None

  def >>(d: Dataflow): Dataflow = this
  def <<(d: Dataflow): Dataflow = this

  def >>(c: Container): Dataflow = {
    app.setRxEndpoint(c, this)

    dst = Option(c)
    this
  }
  def <<(c: Container): Dataflow = {
    app.setTxEndpoint(c, this)

    src = Option(c)
    this
  }

  def isConnected = (src.isDefined && dst.isDefined)

  override def generate = "\nDATAFLOW GENERATED HERE"

  def doSend(): Boolean = {
    // check end-points
    require(isConnected, "The dataflow endpoints are not defoned")

    // propagates data
    val result = transform(src.get, dst.get)

    // invoke the callback for the sender & receiver
    if (result) {
      src.get.onDataSent
      dst.get.onDataReceived
    }

    result
  }

  override def toString = "(" + src + " -> " + dst + ")"
}