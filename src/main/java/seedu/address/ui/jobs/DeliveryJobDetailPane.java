package seedu.address.ui.jobs;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.jobs.DeliveryDate;
import seedu.address.model.jobs.DeliveryJob;
import seedu.address.ui.UiPart;
import seedu.address.ui.person.PersonCard;

/**
 * DeliveryJobDetailPane.
 */
public class DeliveryJobDetailPane extends UiPart<Region> {

    private static final String FXML = "DeliveryJobDetailPane.fxml";
    private static final String BUTTON_LABEL_NOTCOMPLETE = "❌";
    private static final String BUTTON_LABEL_COMPLETE = "✅";
    private static final String BUTTON_LABEL_SHOW = "🔓";
    private static final String BUTTON_LABEL_HIDE = "🔒";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final DeliveryJob job;
    private boolean hideSensitive;

    private Consumer<DeliveryJob> handleEdit;
    private Consumer<DeliveryJob> handleComplete;
    private Consumer<DeliveryJob> handleDelete;
    private Consumer<Boolean> handleHide;

    @FXML
    private StackPane senderContactInfoPlaceholder;
    @FXML
    private StackPane recipientContactInfoPlaceholder;
    @FXML
    private Label label;
    @FXML
    private Label id;
    @FXML
    private Label deliveryTimeDate;
    @FXML
    private Label deliveryTimeSlot;
    @FXML
    private Label earning;
    @FXML
    private Label earningDollar;
    @FXML
    private Label earningCent;
    @FXML
    private TextArea description;
    @FXML
    private Button completeButton;
    @FXML
    private Label deliveryAddress;
    @FXML
    private GridPane contactDetailPane;
    @FXML
    private Button toggleContactButton;
    @FXML
    private VBox detailMessageContainer;
    @FXML
    private VBox invalidMessageContainer;

    /**
     * Constructs a DeliveryJobDetailPanel.
     *
     * @param job
     */
    public DeliveryJobDetailPane(DeliveryJob job) {
        this(job, false);
    }

    /**
     * Constructs a DeliveryJobDetailPanel with preconfig contact pane visibiity.
     *
     * @param job
     */
    public DeliveryJobDetailPane(DeliveryJob job, boolean hideSensitive) {
        super(FXML);
        this.job = job;
        this.hideSensitive = hideSensitive;
    }

    /**
     * fills Inner Parts.
     *
     * @param ab
     */
    public void fillInnerParts(ReadOnlyAddressBook ab) {
        label.setText(job.getJobId());

        job.getDeliveryDate().ifPresentOrElse(val -> {
            if (val.date.equals(DeliveryDate.placeholder().toString())) {
                deliveryTimeDate.setText("Not scheduled");
            } else {
                deliveryTimeDate.setText(val.date);
            }
        }, () -> {
            deliveryTimeDate.setText("Not scheduled");
        });

        job.getDeliverySlot().ifPresentOrElse(val -> {
            deliveryTimeSlot.setText(val.getDescription());
        }, () -> {
            deliveryTimeSlot.setText("Not scheduled");
        });

        job.getEarning().ifPresentOrElse(val -> {
            earningDollar.setText(val.dollar);
            earningCent.setText(val.cent);
        }, () -> {
            earningDollar.setText("0");
            earningCent.setText("00");
        });

        if (ab.getPersonById(job.getSenderId()).isPresent() && ab.getPersonById(job.getRecipientId()).isPresent()) {
            PersonCard cardSender = new PersonCard(ab.getPersonById(job.getSenderId()).get(), "Sender");
            cardSender.getRoot().getStyleClass().add("job_person_card");
            senderContactInfoPlaceholder.getChildren().add(cardSender.getRoot());

            PersonCard cardRecipient = new PersonCard(ab.getPersonById(job.getRecipientId()).get(), "Recipient");
            cardRecipient.getRoot().getStyleClass().add("job_person_card");
            deliveryAddress.setText(ab.getPersonById(job.getRecipientId()).get().getAddress().toString());
            recipientContactInfoPlaceholder.getChildren().add(cardRecipient.getRoot());
        } else {
            setErrorMode();
        }

        description.setText(job.getDescription());
        handleEdit = (job) -> {};
        handleComplete = (job) -> {};
        handleDelete = (job) -> {};
        updateCompleteButton();
        togglehideSensitive();
    }

    /**
     * Sets the edit handler.
     *
     * @param handler
     */
    public void setEditHandler(Consumer<DeliveryJob> handler) {
        handleEdit = handler;
    }

    /**
     * Sets the complete handler.
     *
     * @param handler
     */
    public void setCompleteHandler(Consumer<DeliveryJob> handler) {
        handleComplete = handler;
    }

    /**
     * Sets the delete handler.
     *
     * @param handler
     */
    public void setDeleteHandler(Consumer<DeliveryJob> handler) {
        handleDelete = handler;
    }

    /**
     * Sets the hide handler.
     *
     * @param handler
     */
    public void setHideHandler(Consumer<Boolean> handler) {
        toggleContactButton.setVisible(true);
        handleHide = handler;
    }

    @FXML
    void handleEditAction() {
        logger.info("[Event] handleEditAction");
        handleEdit.accept(job);
    }

    @FXML
    void handleCompleteAction() {
        logger.info("[Event] handleCompleteAction");
        updateCompleteButton();
        handleComplete.accept(job);
    }

    @FXML
    void handleDeleteAction() {
        logger.info("[Event] handleDeleteAction");
        handleDelete.accept(job);
    }

    @FXML
    void handleHideAction() {
        logger.info("[Event] handleHideAction");
        this.hideSensitive = !hideSensitive;
        togglehideSensitive();
        handleHide.accept(hideSensitive);
    }

    private void togglehideSensitive() {
        if (!hideSensitive) {
            toggleContactButton.setText(BUTTON_LABEL_SHOW);
        } else {
            toggleContactButton.setText(BUTTON_LABEL_HIDE);
        }
        contactDetailPane.setVisible(!hideSensitive);
    }

    private void updateCompleteButton() {
        if (job.getDeliveredStatus()) {
            completeButton.setText(BUTTON_LABEL_NOTCOMPLETE);
        } else {
            completeButton.setText(BUTTON_LABEL_COMPLETE);
        }
    }

    private void setErrorMode() {
        deliveryAddress.setText("");
        completeButton.setDisable(true);
        detailMessageContainer.setVisible(false);
        invalidMessageContainer.setVisible(true);
    }
}
