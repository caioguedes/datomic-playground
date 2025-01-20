(ns playground.datetime)

(defn dt-ym-eq [dt inst]
  (and (= (.getYear dt) (.getYear inst))
       (= (.getMonth dt) (.getMonth inst))))

(defn dt-y-eq [dt inst]
  (= (.getYear dt) (.getYear inst)))