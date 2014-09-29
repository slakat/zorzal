# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20140929202129) do

  create_table "alerts", force: true do |t|
    t.integer  "fromUserID"
    t.integer  "toUserID"
    t.integer  "state"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "alerts", ["fromUserID"], name: "index_alerts_on_fromUserID"
  add_index "alerts", ["toUserID"], name: "index_alerts_on_toUserID"

  create_table "contacts", force: true do |t|
    t.integer  "contact_id"
    t.string   "keyword"
    t.string   "nick"
    t.integer  "user_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "contacts", ["contact_id"], name: "index_contacts_on_contact_id"
  add_index "contacts", ["user_id"], name: "index_contacts_on_user_id"

  create_table "devices", force: true do |t|
    t.string   "regID"
    t.integer  "user_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "users", force: true do |t|
    t.string   "username"
    t.string   "name"
    t.string   "password_digest"
    t.string   "email"
    t.string   "role"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
