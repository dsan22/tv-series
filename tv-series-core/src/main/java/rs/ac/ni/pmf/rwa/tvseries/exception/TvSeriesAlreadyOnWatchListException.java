package rs.ac.ni.pmf.rwa.tvseries.exception;

public class TvSeriesAlreadyOnWatchListException extends  RuntimeException{

    public TvSeriesAlreadyOnWatchListException(final String username , final Integer tvSeriesId)
    {
        super("Tv Series with id ["+tvSeriesId+"] is already on "+username+"'s watch list!");
    }
}
