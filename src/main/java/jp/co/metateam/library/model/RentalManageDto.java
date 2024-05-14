package jp.co.metateam.library.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jp.co.metateam.library.values.RentalStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 貸出管理DTO
 */
@Getter
@Setter
public class RentalManageDto {

    private Long id;

    @NotEmpty(message="在庫管理番号は必須です")
    private String stockId;

    @NotEmpty(message="社員番号は必須です")
    private String employeeId;

    @NotNull(message="貸出ステータスは必須です")
    private Integer status;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="貸出予定日は必須です")
    private Date expectedRentalOn;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="返却予定日は必須です")
    private Date expectedReturnOn;

    private Timestamp rentaledAt;

    private Timestamp returnedAt;

    private Timestamp canceledAt;

    private Stock stock;

    private Account account;

    public Optional<String> isStatusError (Integer oldStatus, Integer status) {
        if (oldStatus == RentalStatus.RENT_WAIT.getValue() && status == RentalStatus.RETURNED.getValue()) {
            return Optional.of ("「貸出待ち」から「返却済み」は選択できません ");
        } else if (oldStatus == RentalStatus.RENTAlING.getValue() && status == RentalStatus.RENT_WAIT.getValue()) {
            return Optional.of ( "「貸出中」から「貸出待ち」は選択できません ");
        } else if (oldStatus == RentalStatus.RENTAlING.getValue() && status == RentalStatus.CANCELED.getValue()) {
            return Optional.of ("「貸出中」から「キャンセル」は選択できません ");
        } else if (oldStatus == RentalStatus.RETURNED.getValue() && status == RentalStatus.RENT_WAIT.getValue()) {
            return Optional.of ("「返却済み」から「貸出待ち」は選択できません ");
        } else if (oldStatus == RentalStatus.RETURNED.getValue() && status == RentalStatus.RENTAlING.getValue()) {
            return Optional.of ("「返却済み」から「貸出中」は選択できません ");
        } else if (oldStatus == RentalStatus.RETURNED.getValue() && status == RentalStatus.CANCELED.getValue()) {
            return Optional.of ( "「返却済み」から「キャンセル」は選択できません ");
        } else if (oldStatus == RentalStatus.CANCELED.getValue() && status == RentalStatus.RENT_WAIT.getValue()) {
            return Optional.of ("「キャンセル」から「貸出待ち」は選択できません ");
        } else if (oldStatus == RentalStatus.CANCELED.getValue() && status == RentalStatus.RENTAlING.getValue()) {
            return Optional.of ("「キャンセル」から「貸出中」は選択できません ");
        } else if (oldStatus == RentalStatus.CANCELED.getValue() && status == RentalStatus.RETURNED.getValue()) {
            return Optional.of ("「キャンセル」から「返却済み」は選択できません ");
        } else {
            return Optional.empty();
        }
    }
}
