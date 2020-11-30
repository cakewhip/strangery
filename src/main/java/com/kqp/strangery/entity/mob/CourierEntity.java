package com.kqp.strangery.entity.mob;

import com.kqp.strangery.entity.ai.MoveToTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class CourierEntity extends PathAwareEntity {
    private static final String RECEIVER_ID_KEY = "Receiver";
    private static final String MAIL_KEY = "Mail";

    public PlayerEntity receiver;
    public ItemStack mail;

    public CourierEntity(EntityType type, World world) {
        super(type, world);
    }

    public void makeHateMailCourier(PlayerEntity attacker, MobEntity victim) {
        this.receiver = attacker;
        this.mail = generateHateMail(attacker, victim);

        this.setTarget(receiver);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new MoveToTargetGoal(this, 1.0D, 256));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(3, new LookAroundGoal(this));

        this.targetSelector.add(0, new TrackReceiverGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isClient()) {
            if (receiver != null) {
                if (distanceTo(receiver) < 2.5D) {
                    receiver
                        .sendMessage(new TranslatableText("entity.strangery.courier.intro"), false);
                    receiver
                        .sendMessage(new TranslatableText("entity.strangery.courier.bye"), false);

                    Vec3d dropVec = receiver.getPos().subtract(this.getPos())
                        .normalize()
                        .multiply(0.2D, 0D, 0.2D)
                        .add(0D, 0.2D, 0D);

                    ItemEntity itemEntity = this.dropStack(mail);
                    itemEntity.setVelocity(dropVec);
                    itemEntity.updatePosition(
                        itemEntity.getX(),
                        itemEntity.getY() + 1D,
                        itemEntity.getZ()
                    );

                    this.receiver = null;
                    this.setTarget(null);
                }
            }
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);

        if (receiver != null) {
            tag.putInt(RECEIVER_ID_KEY, receiver.getEntityId());
        }

        if (mail != null) {
            tag.put(MAIL_KEY, mail.toTag(new CompoundTag()));
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);

        if (tag.contains(RECEIVER_ID_KEY)) {
            receiver = (PlayerEntity) world.getEntityById(tag.getInt(RECEIVER_ID_KEY));
            this.setTarget(receiver);
        }

        if (tag.contains(MAIL_KEY)) {
            mail = ItemStack.fromTag(tag.getCompound(MAIL_KEY));
        }
    }

    public static DefaultAttributeContainer.Builder createCourierAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 256.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.333D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D);
    }

    class TrackReceiverGoal extends TrackTargetGoal {
        public TrackReceiverGoal(CourierEntity courier) {
            super(courier, false, false);
        }

        public boolean shouldContinue() {
            return ((CourierEntity) mob).receiver != null || super.shouldContinue();
        }

        @Override
        public boolean canStart() {
            PlayerEntity receiver = ((CourierEntity) mob).receiver;

            if (receiver != null) {
                this.target = receiver;
                return true;
            }

            return false;
        }
    }

    public static ItemStack generateHateMail(PlayerEntity attacker, MobEntity victim) {
        String attackerName = getTagFriendlyName(attacker);
        String victimName = getTagFriendlyName(victim);

        String title = HATE_MAIL_TITLES[RANDOM.nextInt(HATE_MAIL_TITLES.length)]
            .replace("${player}", attackerName)
            .replace("${victim}", victimName);
        title = title.substring(0, Math.min(32, title.length()));

        String body = HATE_MAIL_BODIES[RANDOM.nextInt(HATE_MAIL_BODIES.length)]
            .replace("${player}", attackerName)
            .replace("${victim}", victimName);
        body = "[{text:'" + body + "'}]";

        CompoundTag tag = new CompoundTag();
        tag.putInt("generation", 2);
        tag.putString("author", victim.getDisplayName().asString());
        tag.putString("title", title);

        ListTag pagesTag = new ListTag();
        pagesTag.add(StringTag.of(body));
        tag.put("pages", pagesTag);

        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        book.setTag(tag);

        return book;
    }

    /**
     * Returns a tag-friendly string of the entity's name.
     * This includes separating the name if it's a translation key.
     *
     * @param entity entity
     * @return string
     */
    private static String getTagFriendlyName(Entity entity) {
        return entity instanceof PlayerEntity ? entity.getDisplayName().asString() : (
            entity.getCustomName() != null ? entity.getCustomName().asString() :
                "'}, {translate:'" + entity.getType().getTranslationKey() + "'}, {text:'"
        );
    }

    private static final Random RANDOM = new Random();

    // !IMPORTANT! the longest possible title is 32 characters
    // This includes any formatted in names
    // Anything longer will get truncated
    private static final String[] HATE_MAIL_TITLES = {
        "YOU SUCK!",
        "YOU SUCK!!11!",
        "HOW COULD U DO THIS?",
        "WHY DID U KILL ME!?!",
        "HEY YOU",
        "HEY ${player}",
        "TO: ${player}",
        "why",
        "why why why why why why",
        "HOW",
        "i hate u",
        "you suck"
    };

    private static final String[] HATE_MAIL_BODIES = {
        "I\\'M GONNA HAUNT YOU NOW",
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        "DEAR ${player},\\nI HATE YOU!!!!\\n\\nFROM: ${victim}",
        "DEAR ${player},\\nU SUX!!!!!\\n\\nFROM: ${victim}",
        "i had a family",
        "I HAD A FAMILY",
        "i will FIND you\\n\\nFROM: ${victim}"
    };
}
