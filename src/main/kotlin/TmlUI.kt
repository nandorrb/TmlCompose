import TmlBuilder.div
import TmlBuilder.p
import TmlBuilder.span
import TmlBuilder.td
import TmlBuilder.th
import TmlBuilder.tr

fun main() {
    val newScope2 = TmlScope("t:container").apply {
        t.actionlink(event = "onClick", context = "user123") {
            t.form(
                zone = "myZone",
                secure = false,
                validationId = "myValidationId"
            ) {
                // Additional content inside Form
                div {
                    p { +"Inside form paragraph" } // ✅ Fix: Use `+` to add text content
                    span { +"Inside form span" } // ✅ Fix: Use `+` to add text content
                }
            }

            tr {
                th {}
            }
            tr {
                td {}
            }

            div {
                p { +"Outside paragraph" }
                span { +"Outside span" }
            }
        }
    }

    println(newScope2.print())
}
