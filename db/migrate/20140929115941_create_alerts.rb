class CreateAlerts < ActiveRecord::Migration
  def change
    create_table :alerts do |t|
      t.integer :fromUserID
      t.integer :toUserID
      t.integer :state

      t.timestamps
    end

    add_index :alerts, :fromUserID
    add_index :alerts, :toUserID

  end
end
