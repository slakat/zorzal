class CreateContacts < ActiveRecord::Migration
  def change
    create_table :contacts do |t|
      t.integer :contact_id
      t.string :keyword
      t.string :nick
      t.integer :user_id

      t.timestamps
    end

    add_index :contacts, :contact_id
    add_index :contacts, :user_id

  end
end
