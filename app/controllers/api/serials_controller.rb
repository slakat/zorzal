module Api
  class SerialsController < ApiApplicationController

    before_filter :token_admin, only: [:create, :update, :destroy]
    before_filter :token_user, only: [:add, :remove, :watch, :unwatch, :seen, :unseen]

    before_action :set_serial, except: [:index, :create, :new]
    before_action :set_reviews, only: [:show, :reviews]
    before_action :set_posts, only:[:show, :posts]


    # GET /serials
    # GET /serials.json
    def index
      respond_with Serial.all
    end

    # GET /serials/1
    # GET /serials/1.json
    def show
      respond_with @serial
    end

    # POST /serials
    # POST /serials.json
    def create
      @serial = Serial.new(serial_params)
      render json: {success: "true", message: "Series has been created", series: @serial}
    end

    # PATCH/PUT /serials/1
    # PATCH/PUT /serials/1.json
    def update
      @serial.update(serial_params)
      render json: {success: "true", message: "Series has been updated", series: @serial}
    end

    # DELETE /serials/1
    # DELETE /serials/1.json
    def destroy
      if @serial
        @serial.destroy
        render json: {success: "true", message: "Series has been destroyed"}
      else
        render json: {success: "false", message: "Series doesn't exist"}
      end
    end

    def add
      if token_user
        if token_user.serials.include?(@serial)
          render json: {success: "false", message: "User already has series.", series: @serial}
        else
          token_user.serials << @serial
          render json: {success: "true", message: "User has added series.", series: @serial}
        end
      end
    end

    def remove
      if token_user
        if token_user.serials.include?(@serial)
          token_user.serials.delete(@serial)
          render json: {success: "true", message: "User has removed this series.", series: @serial}
        else
          render json: {success: "false", message: "User doesn't have added this series", series: @serial}
        end
      end
    end

    def watch
      if token_user
        if token_user.watchlist.serials.include?(@serial)
          render json: {success: "false", message: "User already has series in watchlist", series: @serial}
        else
          token_user.watchlist.serials << @serial
          render json: {success: "true", message: "User has added series to watchlist.", series: @serial}
        end
      end
    end

    def unwatch
      if token_user
        if token_user.watchlist.serials.include?(@serial)
          token_user.watchlist.serials.delete(@serial)
          render json: {success: "true", message: "Series has been removed from user's watchlist", series: @serial}
        else
          render json: {success: "false", message: "User doesn't have this series in watchlist", series: @serial}
        end
      end
    end

    def reviews
      respond_with @serial.reviews
    end

    def posts
      respond_with @serial.posts
    end

    private
      # Use callbacks to share common setup or constraints between actions.
      def set_serial
        @serial = Serial.find(params[:id])
      end

      def set_reviews
        @reviews = @serial.reviews
      end

      def set_posts
        @posts = @serial.posts
      end

      # Never trust parameters from the scary internet, only allow the white list through.
      def serial_params
        params.require(:serial).permit(:name, :date, :genre, :description)
      end
  end
end