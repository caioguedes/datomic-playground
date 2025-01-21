(ns playground.datetime
  (:require [clojure.instant :refer [read-instant-date]]))

(defn dt-eq
  ([dt yyyy]
   (= (.getYear dt) (.getYear (read-instant-date yyyy))))
  ([dt yyyy MM]
   (and (dt-eq dt yyyy)
        (= (.getMonth dt) (.getMonth (read-instant-date (str yyyy "-" MM)))))))

(comment
  (dt-eq #inst "2025-01-01" "2025")
  (dt-eq #inst "2025-02-01" "2025")
  (dt-eq #inst "2025-02-01" "2025" "01")
  (dt-eq #inst "2025-12-01" "2025" "12")

  :hodor)