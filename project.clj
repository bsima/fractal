(defproject fractal "0.1.0-SNAPSHOT"
  :description "An update to @nakkaya's blog post"
  :url "http://bsima.me/fractal"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojars.nakkaya/commons-math "2.1"]
                 [clj-time "0.7.0"]]
  :main ^:skip-aot fractal.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
