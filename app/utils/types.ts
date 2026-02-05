export interface DartApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface User {
  username: string;
  email: string;
}

export interface LoginResponse {
  token: string;
}

// User DTOs
export interface UserResponse {
  id: string;
  username: string;
  roles: RoleResponse[];
}

export interface UserRequest {
  username: string;
  password: string;
}

// Role DTOs
export interface RoleResponse {
  id: string;
  name: string;
  permissions: PermissionResponse[];
}

export interface RoleRequest {
  name: string;
}

// Permission DTOs
export interface PermissionResponse {
  id: string;
  name: string;
}

export interface PermissionRequest {
  name: string;
}

// Todo DTOs
export interface TodoResponse {
  id: string;
  description: string;
  completed: boolean;
  createdAt: string; // ISO string
  updatedAt: string; // ISO string
  createdBy: string;
  updatedBy: string;
  markedForDeletion: boolean;
}

export interface TodoRequest {
  description: string;
  completed: boolean;
}

// Comment DTOs
export interface CommentResponse {
  id: string;
  content: string;
  targetId: string;
  targetType: string; // EEntityTargetType
  authorId: string;
  markedForDeletion: boolean;
}

export interface CommentRequest {
  content: string;
  targetId: string;
  targetType: string; // EEntityTargetType
}

// Community DTOs
export interface CommunityResponse extends AuditInfo {
  name: string;
  description: string;
}

export interface AuditInfo {
  id: string;
  markedForDeletion: boolean;
  createdAt: string; 
  updatedAt: string; 
  createdByUserInfo: UserResponse;
  updatedByUserInfo: UserResponse;
}

export interface CommunityRequest {
  name: string;
  description: string;
}

// Post DTOs
export interface PostResponse {
  id: string;
  title: string;
  content: string;
  communityId: string;
  authorId: string;
  markedForDeletion: boolean;
}

export interface PostRequest {
  title: string;
  content: string;
  communityId: string;
}

// Vote DTOs
export interface VoteResponse {
  id: string;
  targetId: string;
  targetType: string;
  userId: string;
  upvote: boolean;
}

export interface VoteRequest {
  targetId: string;
  targetType: string;
  userId: string;
  upvote: boolean;
}

export interface Post extends AuditInfo {
    id: string;
    title: string;
    content?: string;
    author?: string;
    community: string;
    votes?: number;
    media?: MediaItem[];
}

export type MediaItem =
    | ImageMedia
    | VideoMedia
    | TextMedia
    | PollMedia
    | QuoteMedia
    | CodeMedia
    | FileMedia
    | AudioMedia
    | EventMedia
    | LocationMedia
    | ProductMedia
    | ProfileMedia
    | SurveyMedia
    | AnnouncementMedia
    | CountdownMedia
    | BadgeMedia
    | AchievementMedia
    | MilestoneMedia
    | TestimonialMedia
    | CaseStudyMedia
    | InfographicMedia
    | MapMedia
    | TimelineMedia
    | ChartMedia
    | TableMedia
    | SlideshowMedia
    | AnimationMedia
    | VirtualTourMedia
    | Model3DMedia
    | WebinarMedia
    | WorkshopMedia
    | EbookMedia
    | NewsletterMedia
    | ResearchPaperMedia
    | FAQMedia
    | GalleryMedia
    | LinkMedia
    | SummaryMedia
    | ReflectionMedia
    | ChallengeMedia
    | CollaborationMedia
    | SponsorshipMedia;

interface ImageMedia { type: 'image'; url: string; caption?: string; alt?: string; }
interface VideoMedia { type: 'video'; url: string; caption?: string; poster?: string; }
interface TextMedia { type: 'text'; text: string; }
interface PollMedia { type: 'poll'; question: string; options: string[]; results?: Record<string, number>; }
interface QuoteMedia { type: 'quote'; text: string; author?: string; }
interface CodeMedia { type: 'code'; code: string; language?: string; }
interface FileMedia { type: 'file'; url: string; caption?: string; fileName?: string; }
interface AudioMedia { type: 'audio'; url: string; caption?: string; }
interface EventMedia { type: 'event'; title: string; date: string; description?: string; location?: string; }
interface LocationMedia { type: 'location'; latitude: number; longitude: number; caption?: string; }
interface ProductMedia { type: 'product'; name: string; price?: number; url?: string; caption?: string; }
interface ProfileMedia { type: 'profile'; username: string; displayName?: string; avatarUrl?: string; caption?: string; }
interface SurveyMedia { type: 'survey'; question: string; options: string[]; }
interface AnnouncementMedia { type: 'announcement'; title: string; content: string; }
interface CountdownMedia { type: 'countdown'; event: string; endTime: string; caption?: string; }
interface BadgeMedia { type: 'badge'; name: string; description?: string; imageUrl?: string; }
interface AchievementMedia { type: 'achievement'; name: string; description?: string; date?: string; imageUrl?: string; }
interface MilestoneMedia { type: 'milestone'; name: string; description?: string; date?: string; imageUrl?: string; }
interface TestimonialMedia { type: 'testimonial'; author?: string; content: string; }
interface CaseStudyMedia { type: 'caseStudy'; title: string; content: string; }
interface InfographicMedia { type: 'infographic'; url: string; caption?: string; }
interface MapMedia { type: 'map'; latitude: number; longitude: number; caption?: string; }
interface TimelineMedia { type: 'timeline'; events: { date: string; event: string }[]; caption?: string; }
interface ChartMedia { type: 'chart'; chartType: string; data: ChartData; caption?: string; }
interface TableMedia { type: 'table'; headers: string[]; rows: string[][]; caption?: string; }
interface SlideshowMedia { type: 'slideshow'; slides: { url: string; caption?: string }[]; caption?: string; }
interface AnimationMedia { type: 'animation'; url: string; caption?: string; }
interface VirtualTourMedia { type: 'virtualTour'; url: string; caption?: string; }
interface Model3DMedia { type: '3dModel'; url: string; caption?: string; }
interface WebinarMedia { type: 'webinar'; title: string; date: string; description?: string; }
interface WorkshopMedia { type: 'workshop'; title: string; date: string; description?: string; }
interface EbookMedia { type: 'ebook'; title: string; url?: string; description?: string; }
interface NewsletterMedia { type: 'newsletter'; title: string; url?: string; description?: string; }
interface ResearchPaperMedia { type: 'researchPaper'; title: string; url?: string; abstract?: string; }
interface FAQMedia { type: 'faq'; faqs: { question: string; answer: string }[]; caption?: string; }
interface GalleryMedia { type: 'gallery'; images: { url: string; caption?: string }[]; caption?: string; }
interface LinkMedia { type: 'link'; url: string; caption?: string; }
interface SummaryMedia { type: 'summary'; text: string; }
interface ReflectionMedia { type: 'reflection'; prompt?: string; response?: string; }
interface ChallengeMedia { type: 'challenge'; title: string; description?: string; rules?: string; }
interface CollaborationMedia { type: 'collaboration'; projectTitle?: string; description?: string; roles?: string; }
interface SponsorshipMedia { type: 'sponsorship'; sponsorName?: string; description?: string; url?: string; }

interface ChartData {
    labels: string[];
    datasets: ChartDataset[];
}
interface ChartDataset {
    label?: string;
    data: number[];
    backgroundColor?: string[] | string;
    borderColor?: string[] | string;
}