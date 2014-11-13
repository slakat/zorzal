class CreateAlerts < ActiveRecord::Migration
  def change
    create_table :alerts do |t|
      t.integer :fromUserID
      t.integer :toUserID
      t.integer :state

      t.timestamps
    end

    add_reference :users, :fromUserID, index: true
    add_reference :users, :toUserID, index: true

  end
end
