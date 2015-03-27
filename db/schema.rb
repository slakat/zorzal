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

  create_table "contacts", force: true do |t|
    t.string   "keyword"
    t.string   "nick"
    t.integer  "Users_id"
    t.integer  "contact_id_id"
    t.integer  "user_id_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "devices", force: true do |t|
    t.string   "reg_id"
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
    t.integer  "fromUserID_id"
    t.integer  "toUserID_id"
  end

  add_index "users", ["fromUserID_id"], name: "index_users_on_fromUserID_id"
  add_index "users", ["toUserID_id"], name: "index_users_on_toUserID_id"

end
