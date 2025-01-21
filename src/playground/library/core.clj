(ns playground.library.core
  (:require [datomic.api :as d]
            [playground.tutorial :refer [db-uri]]
            [playground.library.schema :refer [library-schema]]))

;; Queries
;; * Loans that happend in the this month [ok]
;; * Member with most number of loans this year
;; * Members that loaned an specific book
;; * All books that a specific member loand (distinct)
;; * History a member loan
;; * Spacific books is availiable to be loand? If not when?

(def all-loans-q
  '[:find [(pull ?e [*]) ...]
    :where [?e :loan/date]])

(def all-loans-of-year-q
  '[:find [(pull ?e [*]) ...]
    :in $ ?year
    :where
    [?e :loan/date ?date]
    [(playground.datetime/dt-eq ?date ?year)]])

(def all-loans-of-year-month-q
  '[:find [(pull ?e [*]) ...]
    :in $ ?year ?month
    :where
    [?e :loan/date ?date]
    [(playground.datetime/dt-eq ?date ?year ?month)]])

(def all-book-titles-q
  '[:find [?title ...]
    :where [_ :book/title ?title]])

(def loans-by-member-q
  '[:find [(pull ?e [*]) ...]
    :in $ ?member
    :where [?e :loan/member ?member]])

(comment
  ;; Initialize Database
  (def uri (db-uri "library"))
  (d/delete-database uri)
  (d/create-database uri)
  (def conn (d/connect (playground.tutorial/db-uri "library")))
  (def db (d/db conn))
  @(d/transact conn library-schema)
  @(d/transact conn (read-string (slurp "resources/library-samples.edn")))


  (d/q all-loans-q db)
  (d/q all-loans-of-year-q db "2025")
  (d/q all-loans-of-year-q db "2024")
  (d/q all-loans-of-year-month-q db "2024" "11")

  (d/q all-book-titles-q db)
  (d/q loans-by-member-q db [:member/email "member1@example.com"])

  :hodor)