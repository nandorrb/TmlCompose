import androidx.compose.runtime.Composable
import TmlBuilder.TmlContainer
import TmlBuilder.Tr
import TmlBuilder.Th
import TmlBuilder.Label
import TmlBuilder.Td
import TmlBuilder.Checkbox

@Composable
fun TmlUI(scope: TmlScope) {
    scope.TmlContainer {
        Tr {
            Th { Label(forId = "displayTooltip") }
            Td {
                Checkbox(id = "displayTooltip", value = "viewReport.displayTooltip", className = "modOnCheck")
            }
        }
    }
}

fun main() {
    val rootScope = TmlScope("t:container") // Change from "root" to "t:container"
    TmlUI(rootScope)

    val output = rootScope.print()
    println(output)
}
