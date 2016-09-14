package objects.extension

import org.nutz.json.Json

// 包级别扩展
fun <T> T.toJson(): String {
    return Json.toJson(this)
}

inline fun <T> T.with(operations: T.() -> Unit): T {
    operations()
    return this
}