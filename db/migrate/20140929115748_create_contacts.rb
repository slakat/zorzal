class CreateContacts < ActiveRecord::Migration
  def change
    create_table :contacts do |t|
      t.integer :contact_id
      t.string :keyword
      t.string :nick
      t.integer :user_id

      t.timestamps
    end

    add_reference :users, :contact_id, index: true
    add_reference :users, :user_id, index: true

  end
end
