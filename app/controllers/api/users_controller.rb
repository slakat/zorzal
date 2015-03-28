module Api 
  class UsersController < ApiApplicationController

    #before_filter :token_admin, only: [:create, :update, :destroy]
    #before_filter :token_user, only: [:add, :remove, :watch, :unwatch, :seen, :unseen]

    http_basic_authenticate_with :name => "zorzal", :password => "123", except: [:index, :create, :new,
                                                                                 :push_notification,:show,:valid_log_in]
    before_action :set_user, except: [:index, :create, :new]



    # GET /movies
    # GET /movies.json
  def index
    @users = User.all
    respond_to do |format|
      format.json { render json: @users }
      format.xml { render xml: @users }
    end
  end

  def new
    @user = User.new
  end

  def show
    respond_to do |format|
      format.json { render json: @user }
      format.xml { render xml: @user }
    end
  end

  def create
    @user = User.new(user_params)
    #@user.password = Devise.friendly_token
    @user.role = "user"
    respond_to do |format|
      if @user.save
        device = Device.new(reg_id: params[:reg_id], user_id: @user.id)
        device.save
        format.json { render json: { "success" => "true", "code" => 200, "user" => @user }.to_json, status:
                                                                                                         :created }
        format.xml { render xml: @user, status: :created }
      else
        format.json { render json: @user.errors, status: :unprocessable_entity }
        format.xml { render xml: @user.errors, status: :unprocessable_entity }
      end
    end
  end

    def valid_log_in
      @user =User.find_by_email(params[:email])
      respond_to do |format|
        if @user
          format.json { render json: {code: 200}.to_json, status: :found }
          format.xml { render xml: @user, status: :found }
        else
          format.json { render json: @user.errors, status: :unprocessable_entity }
          format.xml { render xml: @user.errors, status: :unprocessable_entity }
        end
      end
    end


  def update
    respond_to do |format|
      if @user.update_attributes(params[:user])
        format.json { head :no_content, status: :ok }
        format.xml { head :no_content, status: :ok }
      else
        format.json { render json: @user.errors, status: :unprocessable_entity }
        format.xml { render xml: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  def destroy
    respond_to do |format|
      if @user.destroy
        format.json { head :no_content, status: :ok }
        format.xml { head :no_content, status: :ok }
      else
        format.json { render json: @user.errors, status: :unprocessable_entity }
        format.xml { render xml: @user.errors, status: :unprocessable_entity }
      end
    end
  end

    def add
      if token_user
        if token_user.movies.include?(@movie)
          render json: {success: "false", message: "User already has movie.", movie: @movie}
        else 
          token_user.movies << @movie
          render json: {success: "true", message: "Movie has been added.", movie: @movie}
        end
      end
    end

    def push_notification
      GCM.host = 'https://android.googleapis.com/gcm/send'
      # https://android.googleapis.com/gcm/send is default

      GCM.format = :json
      # :json is default and only available at the moment

      GCM.key = "AIzaSyButI8OVdmH6r1ZR5lmIo4kS4mRWFlWH4E"
      # this is the apiKey obtained from here https://code.google.com/apis/console/

      #Ariel destination = "APA91bHPbWQH75aI1YStVQjZQYeVpluGC4U2vsBbuiFl97p7lhFhg4t9_NG0Zhf6UYvf6JTpnKX8UbHyIljlvYBjXspjwHgHEH_eeyP53Iqx4Y1EQAYbClOHOU8DChbjo7_1Gbt3B-vjGR8u6A7rjz1G73ibuEPUXOqHnXYzIjBVFzrbi0xAPnI"
      #kathy
      destination = "APA91bF9a9p41waFsiFTX-haqA0TVlLELJujIFR54KdHe5RzvYyJSlFuSeku6tiJdPgDiypfIrplUs5S-e80Qrk89ep8KWZ4vUVMiHWkgXaD_BQHWoFrhnH-kqtBMvz7CQoxTLIFQrKk-5oVuQkeLXARJDa61GhVU4CxrODxyfsgRvcYM9mznzI"

      #mama
      #destination = "APA91bEYNWxwEh4RuEwhnrIvgLE0iUv3LUZOfcdSjlw3nAJKtyKEqPGBbkBy3cSrRkA_iZM1yVIj2ZfrI2qD59vn9Ag6AYbCZtBR0sP5uWLETIbWhFIfpBg3gxO77Z7vDzbhtWhRVkk0mt31o-Bp9QFx38ejDvT88w"
      # can be an string or an array of strings containing the regIds of the devices you want to send

      data = {:key => "LALALA", :key2 => ["My anaconda don't", "de kathy"]}
      # must be an hash with all values you want inside you notification

      #GCM.send_notification( destination )
      # Empty notification

      #GCM.send_notification( destination, data )
      # Notification with custom information

      response=GCM.send_notification( destination, data, :collapse_key => "placar_score_global", :time_to_live => 0, :delay_while_idle => false )
      # Notification with custom information and parameters=end


      #flash.now.alert = response
      respond_to do |format|
        format.json { render json: response }
        #format.xml { render xml: response }
      end
    end

    private
      # Use callbacks to share common setup or constraints between actions.
      def set_user
        @user = User.find(params[:id])
      end


      # Never trust parameters from the scary internet, only allow the white list through.
      def user_params
        params.require(:user).permit(:username,:name,:email,:password)
      end
  end
end