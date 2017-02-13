(ns visitor.core
  (:require [clojure.zip :as z]
            [zip.visit :as zv])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn branch? [node]
  (map? node))

(defn children [node]
  (:children node))

(defn make-node [node children]
  (assoc node :children children))

(def test1 {:children []})

(zv/defvisitor visited :pre [n s]
  (println :pre "node:" n " state:" s)
  {:node n
   :state (assoc s :change "visited")})

(defn test-visit
  [root]
  (let [zipper (z/zipper branch? children make-node root)]
    (zv/visit zipper nil [visited])))
