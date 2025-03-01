import TmlBuilder.TapestryComponent
import TmlBuilder.TmlContainer
import TmlBuilder.td
import TmlBuilder.th
import TmlBuilder.tr
import org.apache.tapestry5.corelib.components.Form


fun main() {
    val newScope2 = TmlContainer {
        TapestryComponent(Form::class.java, mapOf("zone" to "myZone", "secure" to false, "validationId" to "myValidationId")) {
            // Additional content inside Form
        }

        tr {
            td {}
            th {}
        }
    }

    println(newScope2.print())
}
