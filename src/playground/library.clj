(ns playground.library
  (:require [datomic.api :as d]
            [playground.tutorial :refer [db-uri]]
            [playground.library.schema :refer [library-schema library-samples]]))

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
    [(playground.datetime/dt-y-eq ?date ?year)]])

(def all-loans-of-month-year-q
  '[:find [(pull ?e [*]) ...]
    :in $ ?month-year
    :where
    [?e :loan/date ?date]
    [(playground.datetime/dt-ym-eq ?date ?month-year)]])

(def all-book-titles-q
  '[:find [?title ...]
    :where [_ :book/title ?title]])

(def loans-by-member-q
  '[:find [(pull ?e [*]) ...]
    :in $ ?member
    :where [?e :loan/member ?member]])

(comment
  (def uri (db-uri "library"))
  (d/delete-database uri)
  (d/create-database uri)
  (def conn (d/connect (playground.tutorial/db-uri "library")))
  ;; Initialize Database
  @(d/transact conn library-schema)
  @(d/transact conn library-samples)
  (def db (d/db conn))

  (d/q all-loans-q db)
  (d/q all-loans-of-year-q db #inst "2025")
  (d/q all-loans-of-year-q db #inst "2024")
  (d/q all-loans-of-month-year-q db #inst "2024-11")

  (d/q all-book-titles-q db)
  (d/q loans-by-member-q db [:member/email "member1@example.com"])

  :hodor)