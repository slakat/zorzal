module Api
  class ApiApplicationController < ApplicationController
   	skip_before_filter :user_admin
    skip_before_filter :authorize

    respond_to :json

	private
	
	def token_user
		authenticate_or_request_with_http_token do |token, options|
    		if AccessToken.exists?(token: token)
    			user = User.find_by(id: AccessToken.find_by(token: token).user_id)
    		end
  		end
	end

	def token_admin
		authenticate_or_request_with_http_token do |token, options|
    		if AccessToken.exists?(token: token)
    			user_admin?(User.find_by(id: AccessToken.find_by(token: token).user_id))
    		end
  		end
	end
  end
end