class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  #protect_from_forgery with: :null_session #:exception
  protect_from_forgery with: :null_session, if: Proc.new { |c| c.request.format.json? }

  helper_method :current_user
  helper_method :user_admin?

  before_filter :user_admin



  private
  def current_user
    @current_user ||= User.find_by_id(session[:user_id]) if session[:user_id]
  end


  def user_admin
    if User.find_by_id(session[:user_id])
      @user = User.find_by_id(session[:user_id])
      unless @user.role == 'admin'
        redirect_to root_path, notice: "Access Denied" 
      end
    end
  end

  def user_admin?(user)
    if user
      user.role == "admin"
    end
  end


end
