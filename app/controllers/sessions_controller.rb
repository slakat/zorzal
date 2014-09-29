class SessionsController < ApplicationController

skip_before_filter :authorize
  skip_before_filter :user_admin
  def new
  end

  def destroy
    session[:user_id] = nil
    flash.notice = "Logged out!"
    redirect_to root_path
  end

  def create
    user = User.find_by_email(params[:email]) || User.find_by(username: params[:email])
    if user && user.authenticate(params[:password])
        session[:user_id] = user.id
        flash.notice = "Logged in successfully!"
        redirect_to root_path
    else
        flash.notice = "Wrong email or password :(" if params[:email] || params[:password]
        render :new
    end

  end

end
