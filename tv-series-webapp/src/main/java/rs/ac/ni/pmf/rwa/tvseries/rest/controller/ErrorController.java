package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rs.ac.ni.pmf.rwa.tvseries.exception.*;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.ErrorCode;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.ErrorDTO;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ErrorController
{

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleMissingParameter(final MissingServletRequestParameterException e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.GENERAL_REQUEST_ERROR)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleGeneralError(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.GENERAL_REQUEST_ERROR)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(DuplicateIdException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleDuplicateIdException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.DUPLICATE_ID)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(DuplicateUserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleDuplicateUserException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.DUPLICATE_USER)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(IdsNotMatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleIdsNotMatchException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.ID_DOSE_NOT_MATCH)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(IllegalEpisodesNumberException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleIllegalEpisodesNumberException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.BAD_EPISODE_NUMBER)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(IllegalRatingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleIllegalRatingException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.BAD_RATING)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(TvSeriesAlreadyOnWatchListException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleTvSeriesAlreadyOnWatchListException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.TV_SERIES_ALREADY_ON_WATCH_LIST)
				.details(e.getMessage())
				.build();
	}
	@ExceptionHandler(TvSeriesNotOnWatchListException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleTvSeriesNotOnWatchListException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.TV_SERIES_NOT_ON_WATCHLIST)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(UnknownAuthorityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleUnknownAuthorityException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.UNKNOWN_AUTHORITY)
				.details(e.getMessage())
				.build();
	}
	@ExceptionHandler(UnknownTvSeriesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleUnknownTvSeriesException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.UNKNOWN_TV_SERIES)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(UnknownUserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleUnknownUserException(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.UNKNOWN_USER)
				.details(e.getMessage())
				.build();
	}

}












