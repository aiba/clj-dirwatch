(ns clj-dirwatch.core
  (:require [clojure.java.io :as io])
  (:import io.methvin.watcher.DirectoryWatcher
           io.methvin.watcher.DirectoryChangeListener
           io.methvin.watcher.DirectoryChangeEvent
           java.nio.file.Path
           java.nio.file.Paths))

(comment
  (def dir "/tmp/d")
  (def wat (-> (DirectoryWatcher/builder)
               (.path (.toPath (io/file dir)))
               (.listener (reify DirectoryChangeListener
                            (onEvent [^DirectoryChangeEvent event]
                              (println event))))))
  (def fut (.watchAsync wat))

  ,,,

  (.close wat)

  )
