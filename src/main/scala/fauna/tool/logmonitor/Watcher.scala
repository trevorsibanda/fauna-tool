package fauna.tool.logmonitor

import fauna.tool.codegen.Generator

abstract class Watcher[T]() {
  def open: Stream[T]

  def close: Boolean

  def codegen: Generator
}
