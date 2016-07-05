(ns fractal.mandelbrot-quil
  (:require [quil.core :as quil]))

(def ^:dynamic *max-iterations* 100)

(defn eu-distance [[x y z]]
  (quil/sqrt
   (+ (quil/sq x)
      (quil/sq y)
      (quil/sq z))))

(defn color-table []
  (let [mix        (rand-nth
                    [[255 144 30]
                     [30 244 144]
                     [144 30 255]])
        rand-color #([(rand 255) (rand 255) (rand 255)])
        avg        (fn [a b] (/ (+ a b) 2))
        avg-color  (fn [a b] (map avg a b))]
    (vec
     (sort-by
      eu-distance
      (for [x (range (* *max-iterations*))]
        (avg-color mix (rand-color)))))))

(defn relative-x [x]
  (- (* x
        (/ 2.7 (quil/width)))
     1.7))

(defn relative-y [y]
  (- (* y
        (/ 2.0 (quil/height)))
     1.0))

(defn interpolate [t c0 c1]
  (+ (* (- 1 t) c0)
     (* t c1)))



(defn mand [x-init y-init]
  (loop [x 0
         y 0
         i 0]
    (cond
      (and (< i (dec *max-iterations*))
           (< (+ (* x x) (* y y))
              4))
      (recur (+ x-init (* x x) (- (* y y)))
             (+ y-init (* 2 x y))
             (inc i))

      (< i (dec *max-iterations*))
      (let [zn     (quil/sqrt (+ (* x x) (* y y)))
            nu     (/ (quil/log (/ (quil/log zn)
                              (quil/log 2)))
                      (quil/log 2))
            color1 (color-table i)
            color2 (color-table (inc i))
            f      (partial interpolate (- 1 nu))]
        (map f color1 color2))

      :else
      '(0 0 0))))

(defn mandelbrot []
  (quil/sketch
   :title
   "A Mandelbrot Fractal"

   :settings
   #(quil/smooth 2)

   :setup
   (fn []
     (quil/frame-rate 1)
     (quil/background 200))

   :draw
   (fn []
     (quil/background 0)
     (dorun
      (for [x (range (- (quil/width) 1))
            y (range (- (quil/height) 1))]
        (let [x0 (relative-x x)
              y0 (relative-y y)
              c  (mand x0 y0)]
          (quil/set-pixel x y (apply quil/color c))))))

   :size
   [646 400]))


