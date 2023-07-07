package lsd.format

import lsd.format.json.convert

fun parse(document: String?): Map<String, Any> = convert(document) ?: HashMap()
