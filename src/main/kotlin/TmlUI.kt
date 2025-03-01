import TmlBuilder.TmlContainer
import TmlBuilder.form
import TmlBuilder.td
import TmlBuilder.th
import TmlBuilder.tr



fun main() {
    val newScope2 = TmlContainer {
        t.form(
            zone = "myZone",
            secure = false,
            validationId = "myValidationId"
        ) {
            // Additional content inside Form
        }

        form {  }

        tr {
            td {}
            th {}
        }
    }

    println(newScope2.print())
}


