(set-env! :source-paths #{"src"}
          :dependencies '[[org.clojars.nakkaya/commons-math "2.1"]
                          [clj-time "0.7.0"]])

(def +version+ "0.1.0-SNAPSHOT")
(task-options! pom {:project 'fractal
                    :version (str +version+ "-standalone")
                    :description "A general-purpose fractal generator"
                    :license {"MIT"}})

;; == Cider REPL =============================================

(require 'boot.repl)
(swap! boot.repl/*default-dependencies*
       concat '[[cider/cider-nrepl "0.8.2"]])

(swap! boot.repl/*default-middleware*
       conj 'cider.nrepl/cider-middleware)

;; == Fractal Generator ======================================

(deftask run
  []
  (me.bsima.fractal/draw))
