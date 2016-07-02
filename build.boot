(set-env! :source-paths #{"src"}
          :dependencies '[
                          [org.clojars.nakkaya/commons-math "2.1"]
                          [quil "2.4.0"]
                          [clj-time "0.12.0"]
                          [adzerk/bootlaces "0.1.13"]])

(require '[adzerk.bootlaces :refer :all])

(def +version+ "1.0.0-SNAPSHOT")

(bootlaces! +version+)

(task-options!
 pom
 {:project 'me.bsima/fractal
  :version (str +version+ "-standalone")
  :description "A general-purpose fractal generator"
  :license {"MIT" "http://opensource.org/licenses/MIT"}})
