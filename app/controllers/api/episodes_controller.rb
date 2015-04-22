module Api
  class EpisodesController < ApiApplicationController

    before_filter :token_admin, only: [:create, :update, :destroy]
    before_filter :token_user, only: [:seen, :unseen]
    
    before_action :set_season_serial, except: [:create]
    before_action :set_episode, except: [:index, :create, :new]
    before_action :set_reviews, only:[:show, :reviews]
    before_action :set_posts, only:[:show, :posts]

    # GET /episodes
    # GET /episodes.json
    def index
      render json: {episodes: @season.episodes, season: @season}
    end

    # GET /episodes/1
    # GET /episodes/1.json
    def show
      render json: {episode: @episode, season: @season}
    end

    # POST /episodes
    # POST /episodes.json
    def create
      @serial = Serial.find_by(id: params[:serial_id])
      @season = @serial.seasons.find_by(number: params[:season_id])
      @episode = Episode.create(episode_params)
      @episode.season_id = @season.id
      @season.episodes << @episode
      if @episode.id
        render json: {success: "true", message: "Episode has been created", episode: @episode, season: @season}
      else
        render json: {success: "false", message: "Episode couldn't be created", episode: @episode, season: @season, serial: @serial}
      end
    end

    # PATCH/PUT /episodes/1
    # PATCH/PUT /episodes/1.json
    def update
      @episode.update(episode_params)
      render json: {success: "true", message: "Episode has been upadated"}
    end

    # DELETE /episodes/1
    # DELETE /episodes/1.json
    def destroy
      if @episode
        @episode.destroy
        render json: {success: "true", message: "Episode has been deleted."}
      else
        render json: {success: "false", message: "Episode couldn't be deleted."}
      end
    end


    def seen
      if token_user
        if token_user.watched.episodes.include?(@episode)
          render json: {success: "false", message: "User has already seen episode", episode: @episode}
        else
          token_user.watched.episodes << @episode
          token_user.score_update(nil)
          render json: {success: "true", message: "Episode has been marked as seen", episode: @episode}
        end
      end
    end

    def unseen
      if token_user
        if token_user.watched.episodes.include?(@episode)
          token_user.watched.episodes.delete(@episode)
          token_user.score_update(nil)
          render json: {success: "true", message: "Episode has been marked as unseen", episode: @episode}
        else
          render json: {success: "false", message: "User has not seen this episode.", episode: @episode}
        end
      end
    end

    def reviews
      respond_with @episode.reviews
    end

    def posts
      respond_with @episode.posts
    end

    private
      # Use callbacks to share common setup or constraints between actions.
      def set_episode
        @episode = @season.episodes.find_by(number: params[:id])
      end

      def set_season_serial
        @serial = Serial.find(params[:serial_id])
        @season = @serial.seasons.find_by(number: params[:season_id])
      end

      def set_reviews
        @reviews = @episode.reviews
      end

      def set_posts
        @posts = @episode.posts
      end

      # Never trust parameters from the scary internet, only allow the white list through.
      def episode_params
        params.require(:episode).permit(:name, :description, :date, :number, :write, :director, :duration, :season_id, :tag_list)
      end
  end
end