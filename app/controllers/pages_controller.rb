class PagesController < ApplicationController
	#require 'gcm'
  skip_before_filter :user_admin, only: [:home]
  skip_before_filter :authorize, only: [ :home]

  def home

		GCM.host = 'https://android.googleapis.com/gcm/send'
		# https://android.googleapis.com/gcm/send is default

		GCM.format = :json
		# :json is default and only available at the moment

		GCM.key = "AIzaSyButI8OVdmH6r1ZR5lmIo4kS4mRWFlWH4E"
		# this is the apiKey obtained from here https://code.google.com/apis/console/

		destination = "APA91bHPbWQH75aI1YStVQjZQYeVpluGC4U2vsBbuiFl97p7lhFhg4t9_NG0Zhf6UYvf6JTpnKX8UbHyIljlvYBjXspjwHgHEH_eeyP53Iqx4Y1EQAYbClOHOU8DChbjo7_1Gbt3B-vjGR8u6A7rjz1G73ibuEPUXOqHnXYzIjBVFzrbi0xAPnI"
		# can be an string or an array of strings containing the regIds of the devices you want to send

		data = {:key => "LALALA", :key2 => ["My anaconda don't", "de kathy"]}
		# must be an hash with all values you want inside you notification

		#GCM.send_notification( destination )
		# Empty notification

		#GCM.send_notification( destination, data )
		# Notification with custom information

		#response=GCM.send_  notification( destination, data, :collapse_key => "placar_score_global", :time_to_live => 3600, :delay_while_idle => false )
		# Notification with custom information and parameters=end


       #flash.now.alert = response
  


		#gcm = GCM.new("AIzaSyButI8OVdmH6r1ZR5lmIo4kS4mRWFlWH4E")
		#registration_ids= ["APA91bHPbWQH75aI1YStVQjZQYeVpluGC4U2vsBbuiFl97p7lhFhg4t9_NG0Zhf6UYvf6JTpnKX8UbHyIljlvYBjXspjwHgHEH_eeyP53Iqx4Y1EQAYbClOHOU8DChbjo7_1Gbt3B-vjGR8u6A7rjz1G73ibuEPUXOqHnXYzIjBVFzrbi0xAPnI"] # an array of one or more client registration IDs
		#options = {data: {score: "123"}, collapse_key: "updated_score", message: "I'm FUCKING CRYING"}
		#response = gcm.send(registration_ids, options)
		#flash.now.alert = response
	end
end