class User < ActiveRecord::Base
	has_many :callouts, class_name: 'Contact', foreign_key: :user_id
	has_many :callins, class_name: 'Contact', foreign_key: :user_id

	has_many :alertsFrom, class_name: 'Alert', foreign_key: :fromUserID
	has_many :alertsTo, class_name: 'Alert', foreign_key: :toUserID
	has_many :devices

	has_secure_password

	validates :username, presence: true, uniqueness:{case_sensitive: false}
	validates :name, presence: true
	validates :email, presence: true, format: { with: /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})\Z/i }, uniqueness: true
  	validates :role, inclusion: { in: %w{admin user} }



  def to_param
      username
  end

  def self.find(input)
      find_by(username: input)
  end

end
