module Api
  class SeasonsController < ApiApplicationController

    before_filter :token_admin, only: [:create, :update, :destroy]
    #before_filter :token_user, only: [:add, :remove, :watch, :unwatch, :seen, :unseen]
    
    before_action :set_serial
    before_action :set_season, except: [:index, :create ]
    before_action :set_reviews, only:[:show, :reviews]
    before_action :set_posts, only:[:show, :posts]

    # GET /seasons
    # GET /seasons.json
    def index
      render json: {seasons: @serial.seasons, series: @serial}
    end

    # GET /seasons/1
    # GET /seasons/1.json
    def show
      render json: {season: @season, series: @serial}
    end

    # POST /seasons
    # POST /seasons.json
    def create
      @serial = Serial.find(params[:serial_id])
      @season = Season.create(season_params)
      @season.serial_id = @serial.id
      @serial.seasons << @season
      if @season.id
        render json: {success: "true", message: "Season has been created", season: @season, series: @serial}
      else
        render json: {success: "false", message: "Season was not created"}
      end
    end

    # PATCH/PUT /seasons/1
    # PATCH/PUT /seasons/1.json
    def update
      respond_with @season.update(season_params)
    end

    # DELETE /seasons/1
    # DELETE /seasons/1.json
    def destroy
      if @season
        @season.destroy
        render json: {success: "true", message:"Season was successfully deleted", series: @serial}
      else
        render json: {success: "false", message:"Season doesn't exists", series: @serial}
      end
    end

    def reviews
      respond_with @season.reviews
    end

    def posts
      respond_with @season.posts
    end

    private
      # Use callbacks to share common setup or constraints between actions.
      def set_season
        @season = @serial.seasons.find_by(number: params[:id])
      end

      def set_serial
        @serial = Serial.find(params[:serial_id])
      end

      def set_reviews
        @reviews = @season.reviews
      end
      
      def set_posts
        @posts = @season.posts
      end

      # Never trust parameters from the scary internet, only allow the white list through.
      def season_params
        params.require(:season).permit(:description, :date, :number, :serial_id)
      end
  end
end