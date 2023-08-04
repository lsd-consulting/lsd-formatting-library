package lsd.format.xml

import org.dom4j.DocumentException
import org.dom4j.DocumentHelper

fun isValidXml(doc: String): Boolean {
    try {
        DocumentHelper.parseText(doc)
    } catch (e: DocumentException) {
        return false
    }
    return true
}
