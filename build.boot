(set-env! :source-paths #{"src"}
          :dependencies '[[org.clojars.nakkaya/commons-math "2.1"]
                          [quil "2.2.6"]
                          [clj-time "0.7.0"]])

(def +version+ "0.1.0-SNAPSHOT")
(task-options! pom {:project 'fractal
                    :version (str +version+ "-standalone")
                    :description "A general-purpose fractal generator"
                    :license {"MIT" "http://opensource.org/licenses/MIT"}})

;; == Cider REPL =============================================

(require 'boot.repl)
(swap! boot.repl/*default-dependencies*
       concat '[[cider/cider-nrepl "0.8.2"]])

(swap! boot.repl/*default-middleware*
       conj 'cider.nrepl/cider-middleware)

;; == Fractal Generator ======================================

(require 'me.bsima.fractal
         '[me.bsima.fractal.julia :as j]
         '[quil.core :as q])

;; == Example ================================================
;; From the Quil docs https://github.com/quil/quil#getting-started

(deftask circles []
  (defn setup []
    (q/smooth)
    (q/frame-rate 1)
    (q/background 200))

  (defn draw []
    (q/stroke (q/random 255))
    (q/stroke-weight (q/random 10))
    (q/fill (q/random 255))

    (let [diam (q/random 100)
          x    (q/random (q/width))
          y    (q/random (q/height))]
      (q/ellipse x y diam diam)))

  (q/defsketch example
    :title "Oh so many grey circles"
    :setup setup
    :draw draw
    :size [323 200]))
