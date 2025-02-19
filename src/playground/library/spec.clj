(ns playground.library.spec
  (:require [clojure.spec.alpha :as s]))

(s/def :member/name string?)
(s/def :member/email string?)
(s/def :library/member (s/keys :req [:member/name :member/email]))

(s/def :book/isbn integer?)
(s/def :book/title string?)
(s/def :book/author string?)
(s/def :book/publish-year integer?)
(s/def :library/book
  (s/keys :req [:book/isbn :book/title :book/author :book/publish-year]))

(s/def :loan/member :library/member)
(s/def :loan/date inst?)
(s/def :loan/due-date inst?)
(s/def :loan/books (s/coll-of :library/book :distinct true :min-count 1))
(s/def :library/loan
  (s/keys :req [:loan/member :loan/date :loan/due-date :loan/books]))

(s/def :member/loans (s/coll-of :library/loan))
