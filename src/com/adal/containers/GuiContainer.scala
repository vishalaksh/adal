/**
 * 31-MAY-2013
 * 
 * Kiev, Ukraine.
 * 
 */
package com.adal.containers

import com.adal.Container
import com.adal.AdalApplication
import com.adal.Component

/**
 * Specific container implements code-generation of an Android-activity.
 * 
 * @author Alex Evseenko
 *
 */
object GuiContainer {
  def apply(name: String, components: List[Component]) (implicit app: AdalApplication) = new GuiContainer(name, components)
}

class GuiContainer(name: String, components: List[Component]) (implicit app: AdalApplication) extends Container(components) {

  override def generate =
	generateHeader+
    generatePackage+
    generateImports+
    generateClass

  def generateHeader = gmlc("AUTOMATICALLY GENERATED BY ADAL-DSL FOR ANDROID")

  def generatePackage =
    "package com.droidek.pviews;\n\n"

  def generateImports =
    "import android.content.Intent;\n"+
	"import android.os.Bundle;\n"+
	"import android.support.v4.app.FragmentActivity;\n\n"

  def generateClass =
    "\npublic class "+name+" extends FragmentActivity {\n"+
    	generateOnCreate+
	"\n} // End of "+name+"\n"

  def generateOnCreate =
    _override._protected._void._method("onCreate", "Bundle saveInstace") {
	  "BODY"
  	}

}