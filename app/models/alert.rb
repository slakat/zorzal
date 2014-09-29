class Alert < ActiveRecord::Base
	belongs_to :alertsFrom, class_name: 'User'
	belongs_to :alertsTo, class_name: 'User'
end
