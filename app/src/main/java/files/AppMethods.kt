package files

/**
 * Created by MF on 03-01-2018.
 */

fun getYearFromDate(date: String?): String {

    // 2017-05-23 format

    if (date != null && !date.isBlank()) {
        val year = date.split("-")
        return year[0]
    } else {
        return ""
    }
}

fun getDateWithCustomFormat(date: String?): String {

    // 2017-05-23 format

    if (date != null && !date.isBlank()) {
        val year = date.split("-")
        var month = ""
        when (year[1].toInt()) {
            1 -> month = "January"
            2 -> month = "February"
            3 -> month = "March"
            4 -> month = "April"
            5 -> month = "May"
            6 -> month = "June"
            7 -> month = "July"
            8 -> month = "August"
            9 -> month = "September"
            10 -> month = "October"
            11 -> month = "November"
            12 -> month = "December"
        }
        return month + " " + year[2] + ", " + year[0]

    } else {
        return ""
    }
}