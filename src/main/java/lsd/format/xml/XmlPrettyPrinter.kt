package lsd.format.xml

import lsd.format.log.log
import org.dom4j.DocumentException
import org.dom4j.DocumentHelper
import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import java.io.IOException
import java.io.StringWriter

private val format = OutputFormat.createPrettyPrint()

fun indentXml(document: String?): String? {
    return document?.let {
        val sw = StringWriter()
        try {
            XMLWriter(sw, format).write(DocumentHelper.parseText(document))
            sw.toString()
        } catch (e: IOException) {
            null
        } catch (e: DocumentException) {
            log().debug(e.message)
            null
        }
    }
}
