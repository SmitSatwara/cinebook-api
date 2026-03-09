package com.smitsatwara.cinebook.job;

import com.smitsatwara.cinebook.model.SeatStatus;
import com.smitsatwara.cinebook.repository.ShowSeatRepository;
import com.smitsatwara.cinebook.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatLockCleanupJob {
    private final ShowSeatRepository showSeatRepository;
    private final RedisService redisService;

    @Scheduled(fixedRate = 60000)
    public void seatCleanUp(){
        showSeatRepository.findAll()
                .stream()
                .filter(showSeat -> showSeat.getStatus()== SeatStatus.LOCKED)
                .forEach(   showSeat -> {
                    if(!redisService.isSeatLocked(showSeat.getShow().getShowId(),showSeat.getSeat().getSeatId())){
                        showSeat.setStatus(SeatStatus.AVAILABLE);
                        showSeatRepository.save(showSeat);
                    }
                        }

                        );

    }
}
