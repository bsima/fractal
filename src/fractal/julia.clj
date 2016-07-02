(ns fractal.julia
  (:use [quil.core]))

;; http://stackoverflow.com/questions/11824815/fast-complex-number-arithmetic-in-clojure
(deftype Complex [^double real ^double imag])

(defn mag-sq [^Complex z]
  (let [x (double (.real z))
        y (double (.imag z))]
    (+ (* x x) (* y y))))

(defn ifn [^Complex c p]
  (let [rc (.real c)
        ic (.imag c)]
    (condp = p
      5 (fn [^Complex z]
          (let [r (double (.real z))
                i (double (.imag z))]
            (Complex. (+ rc (* r r r r r) (* -10 r r r i i) (* 5 r i i i i))
                      (+ ic (* i i i i i) (* -10 r r i i i) (* 5 r r r r i))))))))

(defn count-iterations [max-its escape z_n itfn]
  (let [escape-pred #(> (mag-sq %) escape)]
    (loop [it-count 0
           z z_n]
      (if (or (escape-pred z)
              (< max-its it-count))
        it-count
        (recur (inc it-count) (itfn z))))))

(defn pt-to-plane [x y w h]
  "Maps (x, y) to a point on the complex plane
  in the range ((-2, -2), (2, 2))."
  (Complex. (* 4 (- (/ (double x) w) 0.5))
            (* 4 (- (/ (double y) h) 0.5))))

(defn rgb-of [v]
  "Maps iteration-count (0-10) to a color."
  (color (+ 15 (* 8 v)) 70 70))

(defn setup []
  (frame-rate 50)
  (color-mode :hsb 100))

(defn draw []
  (time
   (let [w (width)
         h (height)
         c (pt-to-plane (mouse-x) (mouse-y) w h)
         iteration-fn (ifn c 5)
         pixels (pixels)]
     (doseq [x (range w)
             y (range h)]
       (let [v (count-iterations 10 4 (pt-to-plane x y w h) iteration-fn)]
         (aset-int pixels (+ x (* y w)) (rgb-of v))))
     (update-pixels))))

#_(defsketch Julia
  :title "Julia"
  :setup setup
  :draw draw
  :renderer :p2d
  :size [480 480])

#_(defrecord Julia
    []
  
  IFractal
  (setup []
    (frame-rate 50)
    (color-mode :hsb 100))
  (draw []
    (time
     (let [w (width)
           h (height)
           c (pt-to-plane (mouse-x) (mouse-y) w h)
           iteration-fn (ifn c 5)
           pixels (pixels)]
       (doseq [x (range w)
               y (range h)]
         (let [v (count-iterations 10 4 (pt-to-plane x y w h) iteration-fn)]
           (aset-int pixels (+ x (* y w)) (rgb-of v))))
       (update-pixels))))
  (sketch [this]
    :title "Julia"
    :setup (:setup this)
    :draw (:draw this)
    :renderer :p2d
    :size [480 48]))

