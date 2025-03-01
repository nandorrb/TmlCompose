import TmlBuilder.TapestryComponent
import TmlBuilder.TmlContainer
import org.apache.tapestry5.corelib.components.Form

//@Composable
//fun TmlUI(): TmlScope {
//    return TmlContainer {
//        Tr {
//            Th { Label(forId = "displayTooltip") }
//            Td {
//                Checkbox(id = "displayTooltip", value = "viewReport.displayTooltip", className = "modOnCheck")
//            }
//        }
//    }
//}





fun main() {
    val myForm = Form() // A Tapestry component instance

    val newScope2 = TmlContainer {
        TapestryComponent(myForm) {
            // Additional custom content if needed
        }
    }

    println(newScope2.print())
}