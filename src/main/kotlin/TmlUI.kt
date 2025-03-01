import androidx.compose.runtime.Composable
import TmlBuilder.TmlContainer
import TmlBuilder.Tr
import TmlBuilder.Th
import TmlBuilder.Label
import TmlBuilder.Td
import TmlBuilder.Checkbox

@Composable
fun TmlUI() {
    TmlContainer {
        Tr {
            Th { Label(forId = "displayTooltip") }
            Td {
                Checkbox(id = "displayTooltip", value = "viewReport.displayTooltip", className = "modOnCheck")
            }
        }
    }
}

fun main() {
    val output = TmlScope("root").apply {
        TmlUI()
    }.print()

    println(output)
}
