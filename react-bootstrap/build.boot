(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.0" :scope "test"]
                  [cljsjs/react-dom "0.14.0-1"]
                  [org.webjars.bower/bootstrap "3.3.5"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "0.27.3-0")

(task-options!
 pom  {:project     'cljsjs/react-bootstrap
       :version     +version+
       :description "The most popular front-end framework, rebuilt for React."
       :url         "https://react-bootstrap.github.io/index.html"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "https://raw.githubusercontent.com/react-bootstrap/react-bootstrap/master/LICENSE"}})

(deftask download-react-bootstrap []
  (download :url      "https://github.com/react-bootstrap/react-bootstrap-bower/archive/v0.27.3.zip"
            :checksum "fe32b73aa12bd5d2cd920652933c873c"
            :unzip    true))

(deftask package []
  (comp
    (download-react-bootstrap)
    (sift :move {#"^react-bootstrap-bower-.*/react-bootstrap.js"
                 "cljsjs/react-bootstrap/development/react-bootstrap.inc.js"
                 #"^react-bootstrap-bower-.*/react-bootstrap.min.js"
                 "cljsjs/react-bootstrap/production/react-bootstrap.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react-bootstrap"
               :requires ["cljsjs.react.dom"])))
