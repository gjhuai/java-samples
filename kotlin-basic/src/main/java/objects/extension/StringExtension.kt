package objects.extension

import org.nutz.json.Json

fun <T> String.fromJson(clazz: Class<T>): T {
    return Json.fromJson(clazz, this)
}