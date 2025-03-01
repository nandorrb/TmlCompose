import TmlBuilder.TmlContainer
import TmlBuilder.td
import TmlBuilder.th
import TmlBuilder.tr


import TmlBuilder.form // ✅ Use renamed function

fun main() {
    val newScope2 = TmlContainer {
        form( // ✅ Fix: Use `FormComponent()` instead of `Form()`
            zone = "myZone",
            secure = false,
            validationId = "myValidationId"
        ) {
            // Additional content inside Form
        }

        tr {
            td {}
            th {}
        }
    }

    println(newScope2.print())
}


