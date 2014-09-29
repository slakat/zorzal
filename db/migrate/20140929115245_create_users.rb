class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :username, unique: true
      t.string :name
      t.string :password_digest
      t.string :email, unique: true
      t.string :role

      t.timestamps
    end
  end
end
