(ns clj-dirwatch.core
  (:require
   [clojure.java.io :as io])
  (:import
   [io.methvin.watcher DirectoryChangeEvent DirectoryChangeListener DirectoryWatcher]))

(defn ^:private event->data [^DirectoryChangeEvent e]
  [(io/as-file (.path e))
   (keyword (.toString (.eventType e)))])

;; returns a watcher that you can call .close on
(defn watch! ^DirectoryWatcher [dir on-event]
  (let [d (io/as-file dir)
        _ (assert (and (.exists d) (.isDirectory d)))
        w (-> (DirectoryWatcher/builder)
              (.path (.toPath d))
              (.listener (reify DirectoryChangeListener
                           (^void onEvent [this ^DirectoryChangeEvent event]
                            (on-event (event->data event)))))
              (.build))]
    (.watchAsync w)
    w))

(comment
  (def dir "/tmp/d")
  (def events (atom []))
  (def wat (watch! dir #(println (pr-str %))))
  ,,,
  (.close wat)
  )
