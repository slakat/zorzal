class Contact < ActiveRecord::Base
  belongs_to :callout, class_name: 'User'
  belongs_to :callin, class_name: 'User'

  def belongs_to?(user)
    self.user_id == user.id
  end


end
